package es.uc3m.coldes.util;

import java.util.List;

import es.uc3m.coldes.model.RoomUserPencilRequest;

/**
 * Clase que implementa una politica de participación de tipo
 * FIFO, en la cual la primera petición entrante es la primera
 * petición que atendera el sistema para asignar el siguiente
 * propietario del pincel.
 * 
 * @author Jose Miguel Blanco García.
 *
 */
public class PolicyFIFO implements Policy{

	/**
	 * Función que se ha de implementar encargada de seleccionar la 
	 * siguiente petición de una lista de peticiones.
	 * En este caso la siguiente petición es la primera de la lista
	 * que se le pasa, o null en caso de no existir peticiones.
	 * 
	 * @param requests Lista de peticiones.
	 * @return Siguiente propietario del pincel.
	 */
	public String getNextUser(List<RoomUserPencilRequest> requests) {
		RoomUserPencilRequest newOwner = null;
		for(RoomUserPencilRequest next : requests){
			if(newOwner == null){
				return next.getUsername();
			}
		}
		return null;
	}

}
