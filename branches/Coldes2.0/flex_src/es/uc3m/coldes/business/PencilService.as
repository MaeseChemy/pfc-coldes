package es.uc3m.coldes.business
{
	import es.uc3m.coldes.entities.Room;
	import es.uc3m.coldes.utils.popup.LoadingPopUp;
	import es.uc3m.coldes.utils.popup.UtilPopUp;
	
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class PencilService
	{
		private var callback:Function;
		private var callbackError:Function;
		private var popUp:LoadingPopUp;
		
		public function PencilService()
		{
		}

		public function pencilBusy(room:Room, username:String, userfunction:Number, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultPencilBusy);
			this.callback = callback;
			service.pencilBusy(room,username,userfunction);
		}
		
		private function resultPencilBusy(event:ResultEvent):void {
			var pencilBusy:Boolean = event.result as Boolean;
			callback(pencilBusy);	
		}
		
		public function addPencilRequest(room:Room, username:String, userfunction:Number, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultAddPencilRequest);
			this.callback = callback;
			service.addPencilRequest(room,username,userfunction);
		}
		
		private function resultAddPencilRequest(event:ResultEvent):void {
			var pencilBusy:Boolean = event.result as Boolean;
			callback(pencilBusy);	
		}
		
		public function removePencilRequest(room:Room, username:String, userfunction:Number, callback:Function):void {
			var service:RemoteObject=new RemoteObject("ColDesService");
			service.addEventListener(FaultEvent.FAULT, error);
			service.addEventListener(ResultEvent.RESULT, resultRemovePencilRequest);
			this.callback = callback;
			service.removePencilRequest(room,username,userfunction);
		}
		
		private function resultRemovePencilRequest(event:ResultEvent):void {
			var pencilBusy:Boolean = event.result as Boolean;
			callback(pencilBusy);	
		}
		
		private function error(event:FaultEvent):void {
			if (event.fault.faultString.indexOf("Session timeout") >= 0) {
				UtilPopUp.showMessagePopUP("SESSION OVER",
									   "Your session is over.");
			} else {
				UtilPopUp.showMessagePopUP("INTERNAL ERROR",
									   "There was an error performing the operation, contact your application administrator.");
			}if (callbackError != null) {
				callbackError();
			}
		}
	}
}