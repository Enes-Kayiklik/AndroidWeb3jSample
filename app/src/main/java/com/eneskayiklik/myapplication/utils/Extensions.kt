package com.eneskayiklik.myapplication.utils

import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Type
import org.web3j.protocol.core.methods.response.EthCall
import org.web3j.utils.Convert
import java.math.BigDecimal

fun EthCall.getTokenCount(outputParams: List<TypeReference<Type<*>>>): BigDecimal? {
    return try {
        val value = FunctionReturnDecoder.decode(
            value, outputParams
        ).firstOrNull()

        Convert.fromWei(value?.value.toString(), Convert.Unit.ETHER)
    } catch (e: Exception) {
        null
    }
}