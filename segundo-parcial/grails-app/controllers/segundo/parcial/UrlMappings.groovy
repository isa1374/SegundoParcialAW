package segundo.parcial

class UrlMappings {

    static mappings = {
        "/divisasActivas"(controller:"activas", action:"consultar")
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
