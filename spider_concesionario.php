<?php 
    //Concesionario Spider.

    
    require_once('SettingsDB.php');
    require_once('DBConnection.php');
    
    $link = 'https://www.milanuncios.com/tienda/santogal-concesionario-oficial-mercedes-63238.htm';
    $dbc = new DBConnection($dbsettings);
    
    extraeLinks($link, $dbc);
    
    function extraeLinks($shopLink, $dbc){
        $archivo = file_get_contents($shopLink);
        $findme   = '"@type": "Product",';
        $extra = strlen($findme);
        $posInicio = strpos($archivo, $findme) + $extra;
        
        $numAd = substr_count($archivo,$findme);
        for ($i = 0; $i < $numAd; $i++) {
            
            $findme   = '"@type": "Product",';
            $extra = strlen($findme);
            $posInicio = strpos($archivo, $findme) + $extra;
            
            $findme   = '"name":';
            $posFinal = strpos($archivo, $findme);
            
            $urlAd = substr($archivo, $posInicio, $posFinal-$posInicio);
            $urlAd = str_replace('"url": "','',$urlAd);
            $urlAd = str_replace('",','',$urlAd);
            extraeAnuncio(trim($urlAd), $dbc);
            
            $findme   = '"description"';
            $posFinal = strpos($archivo, $findme);
            $archivo = substr($archivo, $posFinal+1);
        }

    }
  
    
    function extraeAnuncio($link, $dbc){
        $archivo = file_get_contents($link);
        if ($archivo === false) {
            echo "Error: file_get_contents no puedo acceder a la url";
            exit(1);
        }
        
        $data = InfoAnuncio($archivo, $link);
      


       insertIntoDB($data, $dbc);
        
    }
    
    function insertIntoDB($data, $dbc){
  
              $sql = "Insert into coches values('"
                                            .$data['referencia']."','"
                                            .$data['titulo']."','"
                                            .$data['descripcion']."','"
                                            .$data['precio']."','"
                                            .$data['url']."','"
                                            .$data['km']."','"
                                            .$data['combustible']."','"
                                            .$data['cambio']."','"
                                            .$data['color']."','"
                                            .$data['potencia']."','"
                                            .$data['year']."','"
                                            .$data['npuertas']. "','"
                                            .$data['localizacion']."','"
                                            .$data['imgName']."','"
                                            .$data['numImg']."');";
        
      
      //echo $sql;
        $ResultSet = $dbc->runQuery($sql);
        if ($ResultSet >= 1){
            echo '<h2>Campos Insertados</h2>';
        }
        
    }
    
    /**
     * 
     * 
     */
    function InfoAnuncio($archivo, $link){
        $combustible = getCombustible($archivo);
         $km = getKm($archivo);
         $cambio = getCambio($archivo);
         $potencia = getPotencia($archivo);
         $npuertas = getPuertas($archivo);
         $color = getColor($archivo);
         $year = getYear($archivo);
        $referencia = getReferencia($archivo);
        $titulo = getTitulo($archivo);
        $precio = getPrecio($archivo);        
        $descripcion = getDescripcion($archivo);
        $localizacion = getLocalizacion($archivo);
        
        $imageData=ImagesAnuncio($archivo);
        
       
        $url = $link;

        $data = array(
            'referencia' => $referencia,
            'titulo' => $titulo,
            'precio' => $precio,
            'combustible' => $combustible,
            'km' => $km,
            'year' => $year,
            'potencia' => $potencia,
            'cambio' => $cambio,
            'npuertas' => $npuertas,
            'color' => $color,
            'descripcion' => $descripcion,
            'localizacion' => $localizacion,
            'url' => $url,
            'numImg'=>$imageData['numImg'],
            'imgName'=>$imageData['imgName'],
            );
        return $data;
    }
    
    function ImagesAnuncio($archivo){
        $myString = '1 / ';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $numImg = trim(substr($archivo, $init_pos + $len, 2));
        $numImg=str_replace("<"," ",$numImg);
        $numImg=$numImg-4;
        
        $myString = 'urlp\":\"';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = '_1.jpg?VersionId=';
        $final_pos = strpos($archivo, $myString);
        $nombre = substr($archivo, $init_pos + $len, $final_pos - $init_pos -$len);
        
    
        $imagesData = array(
        'imgName'=>$nombre,
        'numImg'=>$numImg,
        );
        
        return $imagesData;
    }
    
    function getReferencia($archivo){
        $myString = 'Ref:';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
    
        $myString = 'Ref:  ';
        $final_pos = strpos($archivo, $myString);
        $ref = substr($archivo, $init_pos + $len, $final_pos+10);
        
        return $ref;
    }
    
    function getTitulo($archivo){
        $myString = 'Milanuncios - ';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = '</title>';
        $final_pos = strpos($archivo, $myString);
        
        $titulo = substr($archivo,  $init_pos + $len, $final_pos - $init_pos -$len);
        
        return $titulo;
    }
    
    function getPrecio($archivo){
        $myString = '\"price\":{\"cashPrice\":{\"value\":';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = ',\"text\":\"';
        $final_pos = strpos($archivo, $myString);
        $price = substr($archivo, $init_pos + $len, $final_pos - $init_pos);
        $price = explode(",", $price)[0];
        
        return $price;
    }
    
    function getCombustible($archivo){
        $myString = '\"type\":\"fuel\",\"value\":\"';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = '\"},{\"type\":\"hp\",\"value\":';
        $final_pos = strpos($archivo, $myString);
        $fuel = substr($archivo, $init_pos + $len, $final_pos - $init_pos -$len);
        
        return $fuel;
    }
    
    function getKm($archivo){
        $myString = '{\"type\":\"kilometers\",\"value\":';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = '},{\"type\":\"transmission\",\"value\":\"';
        $final_pos = strpos($archivo, $myString);
        $km = substr($archivo, $init_pos + $len, $final_pos - $init_pos -$len);
        
        return $km;
    }
    
    function getYear($archivo){
        $myString = '<span class="ma-AdTag-label" title="año ';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = '">año ';
        $final_pos = strpos($archivo, $myString);
        $year = substr($archivo, $init_pos + $len, $final_pos - $init_pos -$len);
        
        return $year;
    }
    
    function getPotencia($archivo){
        $myString = '\"type\":\"hp\",\"value\":';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = '},{\"type\":\"kilometers\"';
        $final_pos = strpos($archivo, $myString);
        $hp = substr($archivo, $init_pos + $len, $final_pos - $init_pos -$len);
        
        return $hp;
    }
    
    function getCambio($archivo){
        $myString = '},{\"type\":\"transmission\",\"value\":\"';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = '\"},{\"type\":\"warranty\"';
        $final_pos = strpos($archivo, $myString);
        $transmission = substr($archivo, $init_pos + $len, $final_pos - $init_pos -$len);
        //$transmission = explode($myString, $transmission)[0];
        
        return $transmission;
    }
    
    function getPuertas($archivo){
        $myString = '{\"type\":\"doors\",\"value\":';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = '},{\"type\":\"fuel\"';
        $final_pos = strpos($archivo, $myString);
        $doors = substr($archivo, $init_pos + $len, $final_pos - $init_pos -$len);
        if (strlen($doors) > 2) {
            $myString = '{\"type\":\"doors\",\"value\":';
            $len = strlen($myString);
            $init_pos = strpos($archivo, $myString);
            
            $myString = '},{\"type\":\"environmentalLabel\"';
            $final_pos = strpos($archivo, $myString);
            $doors = substr($archivo, $init_pos + $len, $final_pos - $init_pos -$len);
        }
        return $doors;
    }
    
    function getColor($archivo){
        $myString = '\"type\":\"color\",\"value\":\"';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = '\"},{\"type\":\"doors\",';
        $final_pos = strpos($archivo, $myString);
        $color = substr($archivo, $init_pos + $len, $final_pos - $init_pos -$len);
        
        return $color;
    }
    
    function getDescripcion($archivo){
        $myString = 'description" content="';
        $init_pos = strpos($archivo, $myString)+strlen($myString);
        $myString = '" data-reactroot=""/><link d';
        $final_pos = strpos($archivo, $myString);
        
        
        $description = substr($archivo, $init_pos + strlen($init_pos) - 3, $final_pos - $init_pos - strlen($init_pos) + 3);
        
        $description = str_replace("&quot;", "", $description);
        $description = str_replace("'", "", $description);
        $description = str_replace("\"", "", $description);
        $description = str_replace("“", "", $description);
        $description = str_replace("”", "", $description);
        return $description;
    
    }
    
    function getLocalizacion($archivo){
        $myString = '\"city\":{\"name\":\"';
        $len = strlen($myString);
        $init_pos = strpos($archivo, $myString);
        
        $myString = '\",\"slug\"';
        $final_pos = strpos($archivo, $myString);
        $location = substr($archivo, $init_pos + $len, $final_pos - $init_pos -$len);
        $location = explode($myString, $location)[0];
        
        return $location;
    }
?>