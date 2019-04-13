# HGT Reader

HGT Reader es una aplicación desarrollada en Java que permite visualizar mapas de relieve, almacenados en el formato HGT. El mapa se puede ver en diferentes tamaños, y también incluye la opción de exportar a una imagen PNG.

Los colores dependen de la paleta seleccionada, y si está activada o no la interpolación (la transición continua entre las diferentes referencias de la paleta).

## Formato HGT

El formato HGT fue utilizado para almacenar los mapas de elevación obtenidos del [Shuttle Radar Topography Mission (SRTM)](http://www2.jpl.nasa.gov/srtm/)  de la NASA. Los archivos pueden descargarse desde el [Data Center de USGS EROS](https://dds.cr.usgs.gov/srtm/version2_1/SRTM3/).

Para más información del formato HGT, consultar la página de [Preguntas Frecuentes del SRTM](www2.jpl.nasa.gov/srtm/faq.html).

## Paleta

La paleta se puede seleccionar de entre las ya provistas por la aplicación, o cargar una paleta desde un archivo *.pml. La aplicación también incluye un editor (Paleta - Editor de Paleta), donde se puede agregar o quitar referencias de la paleta actual, y guardar en un archivo para su posterior uso.

## Descarga

La última versión de la aplicación puede descargarse desde la sección de [Releases](https://github.com/hernanrocha/hgt-reader/releases).
Para ejecutarse necesita tener [Java](http://www.java.com/) instalado.
