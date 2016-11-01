package segundo.parcial

class UrlMappings {

    static mappings = {
        "/api/"(view:"index")
        "/" (view:"index")
        "500"(view:'/error')
        "404"(view:'/notFound')
        "/divisasActivas"(controller:"activas", action:"consultar")
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
    }
}
