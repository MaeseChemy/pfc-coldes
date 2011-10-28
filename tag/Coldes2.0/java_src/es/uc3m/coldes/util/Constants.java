package es.uc3m.coldes.util;

/**
 * Clase usada para la definición de constantes del
 * sistema.
 * 
 * @author Jose Miguel Blanco García.
 *
 */
public class Constants {
	
	/**
	 * Valor indefinido
	 */
	public static final int UNDEFINED = -1;
	
	/**
	 * Valores para estado de un usuario.
	 */
	public static final int DEACTIVE = 0;
	public static final int ACTIVE = 1;
	
	/**
	 * Posibles valores para la función de los usuarios
	 * dentro de una sala.
	 */
	public static final int OWNER_FUNCTION = 0;
	public static final int MODERATOR_FUNCTION = 1;
	public static final int COLABORATOR_FUNCTION = 2;
	public static final int GUEST_FUNCTION = 3;

	/**
	 * Valores de sala abierta/cerrada
	 */
	public static final int ROOM_CLOSE = 0;
	public static final int ROOM_OPEN = 1;
	
	/**
	 * Valores para el tipo de participación.
	 */
	public static final int ONE_PAINTING = 0;
	public static final int ALL_PAINTING = 1;
	
}
