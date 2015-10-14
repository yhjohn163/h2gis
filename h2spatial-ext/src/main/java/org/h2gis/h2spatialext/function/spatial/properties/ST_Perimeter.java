/**
 * H2GIS is a library that brings spatial support to the H2 Database Engine
 * <http://www.h2database.com>.
 *
 * H2GIS is distributed under GPL 3 license. It is produced by CNRS
 * <http://www.cnrs.fr/>.
 *
 * H2GIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * H2GIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * H2GIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.h2gis.org/>
 * or contact directly: info_at_h2gis.org
 */
package org.h2gis.h2spatialext.function.spatial.properties;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;
import org.h2gis.h2spatialapi.DeterministicScalarFunction;

/**
 * ST_Perimeter returns the perimeter of a polygon or a multipolygon.
 * 
 * @author Erwan Bocher
 */
public class ST_Perimeter extends DeterministicScalarFunction{
   

    public ST_Perimeter() {
        addProperty(PROP_REMARKS, "Returns the length measurement of the boundary of a Polygon or a MultiPolygon. \n"
                + "Distance units are those of the geometry spatial reference system.");
    }

    @Override
    public String getJavaStaticMethod() {
        return "perimeter";
    }
    
    /**
     * Compute the perimeter of a polygon or a multipolygon.
     * @param geometry
     * @return 
     */
    public static Double perimeter(Geometry geometry){        
        if(geometry==null){
            return null;
        }
        if(geometry.getDimension()<2){
            return 0d;
        }
        return computePerimeter(geometry);
    }
    
    /**
     * Compute the perimeter
     * @param geometry
     * @return 
     */
    private static double computePerimeter(Geometry geometry) {
        double sum = 0;
        for (int i = 0; i < geometry.getNumGeometries(); i++) {
            Geometry subGeom = geometry.getGeometryN(i);
            if (subGeom instanceof Polygon) {
                sum += ((Polygon) subGeom).getExteriorRing().getLength();
            } 
        }
        return sum;
    }    
}