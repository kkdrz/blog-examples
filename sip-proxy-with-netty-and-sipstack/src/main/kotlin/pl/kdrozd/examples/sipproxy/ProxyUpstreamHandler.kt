package pl.kdrozd.examples.sipproxy

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.*
import io.netty.channel.socket.nio.NioSocketChannel
import io.pkts.packet.sip.SipMessage
import io.pkts.packet.sip.address.SipURI
import io.sipstack.netty.codec.sip.SipMessageEncoder
import io.sipstack.netty.codec.sip.SipMessageEvent
import io.sipstack.netty.codec.sip.SipMessageStreamDecoder
import org.slf4j.LoggerFactory

class ProxyUpstreamHandler : SimpleChannelInboundHandler<SipMessageEvent>() {
    private val log = LoggerFactory.getLogger(javaClass)

    private var downstreamChannel: Channel? = null

    override fun channelRead0(
        ctx: ChannelHandlerContext,
        event: SipMessageEvent
    ) {
        val msg = event.message
        val upstreamChannel: Channel = ctx.channel()

        val f: ChannelFuture = connectWithDownstream(upstreamChannel, msg)

        f.addListener(ChannelFutureListener { connectionCompleteFuture: ChannelFuture ->
            if (!connectionCompleteFuture.isSuccess) {
                closeAndFlush(upstreamChannel)
            }

            downstreamChannel = connectionCompleteFuture.channel()
            proxyMessageToDownstream(ctx, msg)
        })
    }

    private fun connectWithDownstream(
        upstreamChannel: Channel,
        msg: SipMessage
    ): ChannelFuture {
        val b = Bootstrap()
        b.group(upstreamChannel.eventLoop())
            .channel(NioSocketChannel::class.java)
            .handler(object : ChannelInitializer<Channel>() {
                override fun initChannel(ch: Channel) {
                    with(ch.pipeline()) {
                        addLast("decoder", SipMessageStreamDecoder())
                        addLast("encoder", SipMessageEncoder())
                        addLast("handler", ProxyDownstreamHandler(upstreamChannel))
                    }
                }
            })
        val routeHeader = msg.routeHeader.address.uri as SipURI
        return b.connect(routeHeader.host.toString(), routeHeader.port)
    }

    private fun proxyMessageToDownstream(ctx: ChannelHandlerContext, msg: SipMessage) {
        log.info(
            "\n{} UPSTREAM -> DOWNSTREAM {}\n{}", ctx.channel()
                .remoteAddress(), downstreamChannel?.remoteAddress(), msg
                .toString()
        )
        downstreamChannel?.writeAndFlush(msg)?.addListener(
            ChannelFutureListener { messageProxiedFuture: ChannelFuture ->
                if (messageProxiedFuture.isSuccess) {
                    ctx.channel().read()
                } else {
                    closeAndFlush(messageProxiedFuture.channel())
                }
            })
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        closeAndFlush(ctx.channel())
    }

    companion object {

        fun closeAndFlush(ch: Channel) {
            if (ch.isActive) {
                ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE)
            }
        }

    }

}