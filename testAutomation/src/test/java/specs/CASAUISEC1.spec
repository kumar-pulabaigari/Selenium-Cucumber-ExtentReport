@objects
    header-menu-*                     css            .steps-list>li>span
    Labels-list-*                     css            .labels
    emailtextfield                    xpath          //input[@name='customerEmail']
    customernumber                    xpath          //input[@name='customerContactNumber']
    contexthelp                       xpath          //p[@class='topMargin10 m-bot16 context-help']
= HEADER =
   @on desktop
    header-menu-1:
        aligned horizontally all header-menu-2
        css font-family contains "FrutigerNextPro-Medium" 
   @on desktop     
    header-menu-2:
        aligned horizontally all header-menu-3
        css font-family contains "FrutigerNextPro-Light"
       
= EMAIL TEXTFIELD =
   @on desktop 
    emailtextfield:
        css border-radius is "4px"
        height 56px
        css padding is "0px 11px"
        css line-height is "38px"
        css border is "1px solid rgb(192, 192, 192)"
   @on desktop         
    customernumber:
        css border-radius is "4px"
        height 56px
        css padding is "0px 11px"
        css line-height is "38px"
        css border is "1px solid rgb(144, 144, 144)"  
    @on desktop     
    contexthelp:
        css color is "rgba(144, 144, 144, 1)"
        css font-size is "13.6px"
        css margin-bottom is "16px"
        css margin-top is "10px"
        css font-weight is "500"
        css word-break is "normal"
        
= LABELS =
    @on desktop
    Labels-list-*:
       count any Labels-list-* is 19
