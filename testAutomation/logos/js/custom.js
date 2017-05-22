/*
@ Page custom java script
@ Author: Nirmala G
*/

//Container initialization

$(document).ready(function() {
    testDataTable();
} );

/*Main table initialization to convert datatable*/
function testDataTable(){
	$('#main').DataTable({
		"aLengthMenu": [ [10,25,50,100,-1],[10,25,50,100,"All"] ],
		"iDisplayLength": -1,
		/*"fnRowCallback" : function(nRow, aData, iDisplayIndex){
            $("td:first", nRow).html(iDisplayIndex +1);
            return nRow;
        },*/
    	"oLanguage": {
			"sEmptyTable": " No Test results are available at this time.",
			"sZeroRecords": "No Test results match your query at this time."
		}
		
		
	});
}
