import segundo.parcial.Divisa
class BootStrap {

    def init = { servletContext ->
        new Divisa(imagen:'./images/us.png',nombre:'USD', valor:'1',convert:'false',ver:'1',disponible:'true').save()
        new Divisa(imagen:'./images/us.png',nombre:'USD', valor:'1',convert:'false',ver:'1',disponible:'true').save()
        new Divisa(imagen:'./images/us.png',nombre:'MXN', valor:'1',convert:'false',ver:'2',disponible:'true').save()
    }
    def destroy = {
    }
}
