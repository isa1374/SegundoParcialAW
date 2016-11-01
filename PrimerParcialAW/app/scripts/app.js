var primerApp= angular.module("primerApp", []);
primerApp.controller("primerAppController", function($scope,$filter,$http,$log){
    $scope.valor = 1;
    
    $scope.calcular=function(valor,original){
        /*$http.get('./scripts/datos.json').success(function(data){
            $scope.conversiones = data;
            
        });*/
        $scope.conversiones.resultado=[];
        var or=[];
        or=$filter('filter')($scope.conversiones.valores,{nombre:original});
        var orVal=or[0].valor;
        angular.forEach($scope.conversiones.valores, function(item){
                if(item.convert){
                    var valorRes=Math.floor(valor/orVal*item.valor*1000)/1000;
                    $scope.conversiones.resultado.push({imagen:item.imagen,nombre:item.nombre, valor:valorRes});
                }
        });
    }
     $http.get('./scripts/datos.json').success(function(data){
        $log.debug(data);
        $scope.conversiones = data;
        $log.debug($scope.conversiones.valores);
    });
   
});




