package me.uport.sdk.ethrdid

import kotlinx.coroutines.experimental.runBlocking
import me.uport.sdk.core.Networks
import me.uport.sdk.core.toBytes32String
import me.uport.sdk.jsonrpc.JsonRPC
import me.uport.sdk.jsonrpc.JsonRpcBaseResponse
import me.uport.sdk.jsonrpc.experimental.getLogs
import org.junit.Assert.*
import org.junit.Test
import org.kethereum.extensions.hexToBigInteger
import java.math.BigInteger


class EthrDIDResolverTest {

    @Test
    fun `last change is blank for new address`() = runBlocking {
        val rpc = JsonRPC(Networks.rinkeby.rpcUrl)
        val imaginaryAddress = "0x1234"
        val lastChanged = EthrDIDResolver(rpc).lastChanged(imaginaryAddress)
        assertEquals(BigInteger.ZERO, lastChanged.hexToBigInteger())
    }

    @Test
    fun `last change is non-zero for real address with changed owner`() = runBlocking {
        val rpc = JsonRPC(Networks.rinkeby.rpcUrl)
        val realAddress = "0xf3beac30c498d9e26865f34fcaa57dbb935b0d74"
        val lastChanged = EthrDIDResolver(rpc).lastChanged(realAddress)
        println(lastChanged)
        assertNotEquals(BigInteger.ZERO, lastChanged.hexToBigInteger())
    }



    @Test
    fun `real address with activity has logs`() = runBlocking {
        val rpc = JsonRPC(Networks.rinkeby.rpcUrl)
        val realAddress = "0xf3beac30c498d9e26865f34fcaa57dbb935b0d74"
        val resolver = EthrDIDResolver(rpc)
        val lastChanged = resolver.lastChanged(realAddress).hexToBigInteger()
        val logResponse = rpc.getLogs(resolver.registryAddress, listOf(null, realAddress.toBytes32String()), lastChanged, lastChanged)
        val parsedResponse = JsonRpcBaseResponse.fromJson(logResponse)
        println(parsedResponse.result)
        assertNull(parsedResponse.error)
        assertNotNull(parsedResponse.result)
    }

}