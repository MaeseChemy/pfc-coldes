package es.uc3m.coldes.util;

import java.util.List;

import es.uc3m.coldes.model.RoomUserPencilRequest;

/**
 * Interfaz que define la nomenclatura de los distintos
 * metodos comunes que tendran las distintas politicas de
 * participación del sistema.
 * 
 * @author Jose Miguel Blanco García.
 *
 */
public interface Policy {
	
	/**
	 * Función que se ha de implementar encargada de seleccionar la 
	 * siguiente petición de una lista de peticiones.
	 * 
	 * @param requests Lista de peticiones.
	 * @return Siguiente propietario del pincel.
	 */
	public String getNextUser(List<RoomUserPencilRequest> requests);

}
