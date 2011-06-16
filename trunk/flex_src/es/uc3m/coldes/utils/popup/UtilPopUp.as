package es.uc3m.coldes.utils.popup {

	import flash.display.DisplayObject;
	
	import mx.core.Application;
	import mx.managers.PopUpManager;


	public class UtilPopUp {

		public static function showMessagePopUP(title:String, message:String):MessagePopUp {
			var popUp:MessagePopUp=PopUpManager.createPopUp(DisplayObject(Application.application), MessagePopUp,
				true) as MessagePopUp;
			popUp.data=message;
			popUp.title=title;
			PopUpManager.centerPopUp(popUp);
			return popUp;
		}

		public static function showLoadingPopUP(title:String, message:String):LoadingPopUp {
			var popUp:LoadingPopUp=PopUpManager.createPopUp(DisplayObject(Application.application), LoadingPopUp,
				true) as LoadingPopUp;
			popUp.data=message;
			popUp.title=title;
			PopUpManager.centerPopUp(popUp);
			return popUp;
		}

		public static function showConditionalPopUP(title:String, message:String):ConditionalPopUp {
			var popUp:ConditionalPopUp=PopUpManager.createPopUp(DisplayObject(Application.application), ConditionalPopUp,
				true) as ConditionalPopUp;
			popUp.data=message;
			popUp.title=title;
			PopUpManager.centerPopUp(popUp);
			return popUp;
		}
		
	}
}
