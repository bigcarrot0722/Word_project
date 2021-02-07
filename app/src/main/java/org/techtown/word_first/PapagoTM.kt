package org.techtown.word_first

data class PapagoTM(var message : Message? = null) {
    data class Message(var result : Result? = null){
        data class Result(var translatedText : String? = null)
    }
}
//json으로 내가 원하는 부분의 값을 뽑는 class임