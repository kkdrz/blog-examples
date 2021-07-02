package pl.kdrozd.examples.sipproxy

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.sipstack.netty.codec.sip.SipMessageEncoder
import io.sipstack.netty.codec.sip.SipMessageStreamDecoder
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress

class ProxyApp(private val hostname: String, private val port: Int) {

    private val log = LoggerFactory.getLogger(javaClass)

    private var upstreamChannel: Channel? = null

    fun start() {
        val workGroup = NioEventLoopGroup()
        val bossGroup = NioEventLoopGroup()
        val b = ServerBootstrap()
        
        b.group(bossGroup, workGroup)
            .channel(NioServerSocketChannel::class.java)
            .childHandler(object : ChannelInitializer<Channel>() {
                override fun initChannel(ch: Channel) {
                    with(ch.pipeline()) {
                        addLast("decoder", SipMessageStreamDecoder())
                        addLast("encoder", SipMessageEncoder())
                        addLast("handler", ProxyUpstreamHandler())
                    }
                }
            })
        val socketAddress = InetSocketAddress(hostname, port)
        val f = b.bind(socketAddress).sync()
        log.info("SIP PROXY SERVER READY! Address: {}", socketAddress)
        upstreamChannel = f.channel()
    }

    fun stop() {
        if (upstreamChannel?.isActive == true) {
            upstreamChannel?.close()?.await()
        }
    }

}