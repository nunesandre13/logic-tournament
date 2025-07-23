package org.example.webApi.lenses

import org.example.webApi.dataTransferObjects.PlayerIN
import org.http4k.core.Body
import org.http4k.format.Jackson.auto

val createUserLens = Body.auto<PlayerIN>().toLens()