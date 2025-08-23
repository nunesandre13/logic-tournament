package exceptionError

import dto.ApiError

class ExceptionApiError(val domainError: ApiError): Exception(domainError.message)


//mapa apenas para guardar por um momento