package es.uc3m.coldes.util;

import java.util.List;

import es.uc3m.coldes.model.RoomUserPencilRequest;

public interface Policy {
	
	public String getNextUser(List<RoomUserPencilRequest> requests);

}
