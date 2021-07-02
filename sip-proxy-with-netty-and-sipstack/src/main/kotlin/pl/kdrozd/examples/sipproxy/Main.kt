package pl.kdrozd.examples.sipproxy


fun main() {
    val app = ProxyApp("127.0.0.1", 5060)
    app.start()
}

