package com.eternalreturntoy.data

import com.eternalreturntoy.BuildConfig
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptoUtils {
    private const val AES_MODE = "AES/CBC/PKCS5Padding"
    private val RedDemon = BuildConfig.AbsolutePowerForce.toByteArray()
    private val IV = BuildConfig.BlackRoseFlare.toByteArray()

    fun decrypt(input: String): String {

        val cipher = Cipher.getInstance(AES_MODE)
        val keySpec = SecretKeySpec(RedDemon, "AES")
        val ivSpec = IvParameterSpec(IV)

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val junk = Base64.getDecoder().decode(input)
        val decryptedBytes = cipher.doFinal(junk)

        val synchron = String(decryptedBytes)

        return synchron
    }
}