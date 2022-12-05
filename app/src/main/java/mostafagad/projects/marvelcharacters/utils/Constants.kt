package mostafagad.projects.marvelcharacters.utils

import java.math.BigInteger
import java.security.MessageDigest

object Constants {
    const val BASE_URL = "http://gateway.marvel.com"
    const val timeStamp = "1"
    const val API_KEY = "8f3f0c9f7620fa3ef4af31536c74a72d"
    const val PRIVATE_KEY = "d1fb030b6962099c20e546af7f4298854b9581cd"
    fun hash():String  {
        val input = "$timeStamp$PRIVATE_KEY$API_KEY"
        val md = MessageDigest.getInstance("MD5")
        return  BigInteger(1 ,md.digest(input.toByteArray())).toString(16).padStart(32 , '0')
    }
}