/*
    Add comments, license etc
*/

function GetInfo(infoObject) {
    infoObject.Name = "Beacon";
    infoObject.Description = "This plugin will convert a beacon profile into a Beacon shell file";
    infoObject.Author = "Bluetooth SIG";
    infoObject.Version = "1.0";
    infoObject.IsClient = false;
    return infoObject;
}

function RunPlugin(profiledata) 
{
	// data structure that will be populated with data from the UI
	var templateData = {
	"company_id": "",
	"premises_id": "",
	"beacon_node_id": "",
	"reference_rssi": ""
	};

	// the output will be stored in these vars before being set in the templateData
	var company;
	var beaconNode;
	var premises;
	
	// set the true / false value on the following line to true to read in the data from the service and false to read in from GAP settings
	if (true)
	{
		// This example reads in the data from the services properties
		log("Reading from Service");

		// Get the first service
		var service = profiledata.Services[0];

		// Whatever the first service is (there should only be one) we will use the FULL UUID as the company identifier
		company = spacedUuid(service.UUID);
		
		// parse the major value that represents a store or premises
		var premisesChar = service.Characteristics[0];
		premises = hex16(premisesChar.CustomProperties.GetValue(0));
		
		// parse the minor value that represents the individual beacon node
		var beaconNodeChar = service.Characteristics[1];
		beaconNode = hex16(beaconNodeChar.CustomProperties.GetValue(0));
	}
	else
	{
		// This example reads in the data from the GAP settings
		log("Reading from GAP");
		
		// read in the data from the GAP properties 
		var manufacturerData = profiledata.GAPProperties.ManufacturerData;
		log("ManufacturerData: " + manufacturerData);
		
		company = spacedUuid(manufacturerData.slice(0, 32));
		
		// parse the major value that represents a store or premises
		var premisesData = manufacturerData.slice(-8, -4);
		premises = premisesData.slice(0, 2) + " " +  premisesData.slice(2);	
		
		// parse the minor value that represents the individual beacon node
		var beaconNodeData = manufacturerData.slice(-4);
		beaconNode = beaconNodeData.slice(0, 2) + " " +  beaconNodeData.slice(2);	
	}	
	
	// parse the value for power, we get this from GAP for both versions above
	var rssiNum = parseInt(profiledata.GAPProperties.TXPower);
	var rssiHex = hex8(rssiNum);		
	
	templateData.company_id = company.trim().toLowerCase();
	templateData.premises_id = premises.trim().toLowerCase();
	templateData.beacon_node_id = beaconNode.trim().toLowerCase();
	templateData.reference_rssi = rssiHex;

	log("Company: " + templateData.company_id);
	log("Premises: " + templateData.premises_id);
	log("Beacon Node: " + templateData.beacon_node_id); 
	log("rssi hex: " + rssiHex);

	// process the template	
	var templateContents = FileManager.ReadFile("Beacon.sh.tmpl");
	var templatedData = ProcessTemplate(templateContents, templateData);
	FileManager.CreateFile("Beacon.sh", templatedData);
}

// function to put spaces between the bytes in the company uuid
function spacedUuid(uuid){
	var spacedCompanyUuid = "";
	for (i=0; i<32; i+=2)
	{
		spacedCompanyUuid += uuid.slice(i, i+2) + " ";
	}
	return spacedCompanyUuid;
}

// function to convert value to hex  
function hex8(val) {
    val &= 0xFF;
    var hex = val.toString(16).toUpperCase();
    return ("00" + hex).slice(-2);
}

// function to convert valued to a padded hex string with the bytes seperated with a space character
function hex16(val) {
    val &= 0xFFFF;
    var hex = val.toString(16).toUpperCase();
    var temp = ("0000" + hex).slice(-4);
	return temp.slice(0,2) + " " +  temp.slice(2);
}
