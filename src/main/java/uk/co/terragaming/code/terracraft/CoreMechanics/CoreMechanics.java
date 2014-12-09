package uk.co.terragaming.code.terracraft.CoreMechanics;

import uk.co.terragaming.code.terracraft.CoreMechanics.AccountMechanics.AccountMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.DatabaseMechanics.DatabaseMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.PermissionMechanics.PermissionMechanics;
import uk.co.terragaming.code.terracraft.CoreMechanics.PlayerMechanics.PlayerMechanics;
import uk.co.terragaming.code.terracraft.utils.TerraLogger;

public class CoreMechanics {

	public static void Initialize(){
		TerraLogger.info("CoreMechanics Initialized");
		
		ReloadHandler.Run();
		DatabaseMechanics.Initialize();
		PermissionMechanics.Initialize();
		AccountMechanics.Initialize();
		PlayerMechanics.Initialize();
		
		TerraLogger.info("");
	}
	
	public static void Denitialize(){
		AccountMechanics.Denitalize();
		PlayerMechanics.Deinitialize();
		PermissionMechanics.Deinitialize();
		
		// Do Last...
		DatabaseMechanics.Denitialize();
	}
	
	public static void DownloadData(){
		PermissionMechanics.DownloadData();
	}
}
