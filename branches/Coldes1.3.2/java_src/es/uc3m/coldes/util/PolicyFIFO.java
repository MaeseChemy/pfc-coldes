package es.uc3m.coldes.util;

import java.util.List;

import es.uc3m.coldes.model.RoomUserPencilRequest;

public class PolicyFIFO implements Policy{

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
