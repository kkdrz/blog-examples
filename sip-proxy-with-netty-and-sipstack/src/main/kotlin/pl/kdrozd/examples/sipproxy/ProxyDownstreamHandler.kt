package pl.kdrozd.examples.sipproxy

import io.netty.channel.*
import io.sipstack.netty.codec.sip.SipMessageEvent
import org.slf4j.LoggerFactory
import pl.kdrozd.examples.sipproxy.ProxyUpstreamHandler.Companion.closeAndFlush

class ProxyDownstreamHandler(private val upstreamChannel: Channel) : SimpleChannelInboundHandler<SipMessageEvent>() {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun channelRead0(ctx: ChannelHandlerContext, event: SipMessageEvent) {
        val msg = event.message
        log.info(
            "\n{} UPSTREAM <- DOWNSTREAM {}\n{}\n-------\n\n", upstreamChannel.remoteAddress(), ctx.channel()
                .remoteAddress(), msg
                .toString()
        )
        upstreamChannel.writeAndFlush(msg).addListener(ChannelFutureListener { future: ChannelFuture ->
            if (future.isSuccess) {
                ctx.channel().read()
            } else {
                closeAndFlush(future.channel())
            }
        })
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        closeAndFlush(upstreamChannel)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        closeAndFlush(ctx.channel())
    }
}