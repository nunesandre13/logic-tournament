package domain

data class Id(val id: Long){
    init {
        require(id >= 0) { "Id cannot be negative" }
    }
}