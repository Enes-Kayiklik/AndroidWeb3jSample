package com.eneskayiklik.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.myapplication.utils.CONTRACT_ADDRESS
import com.eneskayiklik.myapplication.utils.WALLET_ADDRESS
import com.eneskayiklik.myapplication.utils.WEB_SOCKET_URL
import com.eneskayiklik.myapplication.utils.getTokenCount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthCall
import org.web3j.protocol.websocket.WebSocketService
import java.net.ConnectException


class ContractViewModel : ViewModel() {

    var state = MutableStateFlow("")
        private set

    init {
        getBalance()
    }

    private fun createWeb3j(): Web3j? {
        val webSocketService = WebSocketService(WEB_SOCKET_URL, true)
        try {
            webSocketService.connect()
        } catch (e: ConnectException) {
            e.printStackTrace()
        }
        return Web3j.build(webSocketService)
    }

    private fun getBalance() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val web3j: Web3j = createWeb3j() ?: return@launch

            val function: org.web3j.abi.datatypes.Function = org.web3j.abi.datatypes.Function(
                "balanceOf",
                listOf(Address(WALLET_ADDRESS)), listOf(object : TypeReference<Uint256>() {}))

            val encodedFunction = FunctionEncoder.encode(function)
            val response: EthCall = web3j.ethCall(
                Transaction.createEthCallTransaction(WALLET_ADDRESS, CONTRACT_ADDRESS, encodedFunction),
                DefaultBlockParameterName.LATEST
            ).sendAsync().get()


            state.value = response.getTokenCount(function.outputParameters)?.toString() ?: "Error When Reading Contract"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}