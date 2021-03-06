package me.uport.sdk.ethrdid

import kotlinx.serialization.json.JSON
import org.junit.Assert.assertEquals
import org.junit.Test

class DDOTest {

    @Test
    fun `can serialize minimal doc`() {
        val doc = DDO("hello")
        val docText = JSON.stringify(doc)
        assertEquals("""
            {"id":"hello","publicKey":[],"authentication":[],"service":[],"@context":"https://w3id.org/did/v1"}
        """.trimIndent(), docText)
    }

    @Test
    fun `can parse example doc`() {
        //language=JSON
        val docText = """
            {
                "@context": "https://w3id.org/did/v1",
                "id": "did:ethr:0xb9c5714089478a327f09197987f16f9e5d936e8a",
                "publicKey": [{
                    "id": "did:ethr:0xb9c5714089478a327f09197987f16f9e5d936e8a#owner",
                    "type" : "Secp256k1VerificationKey2018",
                    "owner" : "did:ethr:0xb9c5714089478a327f09197987f16f9e5d936e8a",
                    "ethereumAddress" : "0xb9c5714089478a327f09197987f16f9e5d936e8a"
                }],
                "authentication": [{
                    "type": "Secp256k1SignatureAuthentication2018",
                    "publicKey": "did:ethr:0xb9c5714089478a327f09197987f16f9e5d936e8a#owner"
                }]
            }
        """.trimIndent()

        val obj: DDO = JSON.parse(docText)

        println(obj)
    }

}