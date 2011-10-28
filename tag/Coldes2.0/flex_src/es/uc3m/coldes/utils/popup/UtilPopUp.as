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
		
		public static function showUserRoomRegisterPopUP(title:String, message:String):UserRoomRegisterPopUp {
			var popUp:UserRoomRegisterPopUp=PopUpManager.createPopUp(DisplayObject(Application.application), UserRoomRegisterPopUp,
				true) as UserRoomRegisterPopUp;
			popUp.data=message;
			popUp.title=title;
			PopUpManager.centerPopUp(popUp);
			return popUp;
		}
		
		public static function showInvitationTypePopUP(title:String, message:String):InvitationTypePopUp {
			var popUp:InvitationTypePopUp=PopUpManager.createPopUp(DisplayObject(Application.application), InvitationTypePopUp,
				true) as InvitationTypePopUp;
			popUp.data=message;
			popUp.title=title;
			PopUpManager.centerPopUp(popUp);
			return popUp;
		}
		
		public static function showSurveyPopUP(title:String, message:String):SurveyPopUp {
			var popUp:SurveyPopUp=PopUpManager.createPopUp(DisplayObject(Application.application), SurveyPopUp,
				true) as SurveyPopUp;
			popUp.data=message;
			popUp.title=title;
			PopUpManager.centerPopUp(popUp);
			return popUp;
		}
		
	}
}
