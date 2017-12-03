<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Highway - Venta de Peajes</title>
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">       
        <link rel="stylesheet" href="css/master.css" type="text/css" >
        <style>
            h2{
                text-align: center;
            }
            
            section{
                margin-left: 250px;
                margin-top: 0px;
                background-color: rgba(255,255,255,0.6);
                width: calc(100% - 250px);
                padding: 20px 0;
                min-height: 100%;
            }

            embed{
                display: table;
                margin: auto;
            }

            summary{
                background-color: rgba(255,255,255,0.8);
                padding: 0 10px;

            }
            
            .informacion{
                padding: 15px;
            }
        </style>
    </head>

    <body>
        <%@include file="_menu.jspf" %>
        <section>
            <h2>Ubicación de las carreteras</h2>
            <div class="informacion">Haga clic en la carretera de su interés para conocer su ubicación:</div>
            <details>
                <summary>Ruta 68</summary>
                <embed src="https://www.google.com/maps/d/u/0/embed?mid=1dRB-CzCljkK09MWyB-BKosbQjoigkZcr" width="550" height="412">
            </details>
            <details>
                <summary>Autopista del Sol</summary>
                <embed src="https://www.google.com/maps/d/u/0/embed?mid=12rRocCRCtiLKwx3BU5-xTDsJJ5P09kvj" width="550" height="412">
            </details>            
            <details>
                <summary>Ruta Guardia Vieja</summary>
                <embed src="https://www.google.com/maps/d/u/0/embed?mid=1AVSpsOUSIUzyT9HKK5D2iv0ESJXPyeYO" width="550" height="412">
            </details>              
            <details>
                <summary>Autopista Troncal Sur</summary>
                <embed src="https://www.google.com/maps/d/u/0/embed?mid=168wBvFQOisUZ38tx0Xm6r_NQc6lkEJq6" width="550" height="412">
            </details>
            <div class="informacion"></div>
        </section>
    </body>
</html>