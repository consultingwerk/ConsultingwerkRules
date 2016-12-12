/**********************************************************************
 * Copyright (C) 2006-2016 by Consultingwerk Ltd. ("CW") -            *
 * www.consultingwerk.de and other contributors as listed             *
 * below.  All Rights Reserved.                                       *
 *                                                                    *
 *  Software is distributed on an "AS IS", WITHOUT WARRANTY OF ANY    *
 *   KIND, either express or implied.                                 *
 *                                                                    *
 *  Contributors:                                                     *
 *                                                                    *
 **********************************************************************/
/*------------------------------------------------------------------------
    File        : TestControl
    Purpose     : 
    Syntax      : 
    Description : 
    Author(s)   : mikef
    Created     : Thu Dec 01 21:52:26 CET 2016
    Notes       : 
  ----------------------------------------------------------------------*/

ROUTINE-LEVEL ON ERROR UNDO, THROW.

USING Progress.Windows.UserControl.
USING Test.* FROM PROPATH .  
USING Progress.Lang.* FROM PROPATH .

CLASS eventwithoutcatch01 INHERITS UserControl: 

	DEFINE PRIVATE VARIABLE components AS System.ComponentModel.IContainer NO-UNDO.
    DEFINE PRIVATE VARIABLE ultraComboEditor1 AS Infragistics.Win.UltraWinEditors.UltraComboEditor NO-UNDO.
    DEFINE PRIVATE VARIABLE ultraButton1 AS Infragistics.Win.Misc.UltraButton NO-UNDO.

	CONSTRUCTOR PUBLIC TestControl ():
        SUPER().
        InitializeComponent().
        THIS-OBJECT:ComponentsCollection:ADD(THIS-OBJECT:components).
        CATCH e AS Progress.Lang.Error:
            UNDO, THROW e.
        END CATCH.

	END CONSTRUCTOR.

	METHOD PRIVATE VOID InitializeComponent ():
		
        /* NOTE: The following method is automatically generated.
        
        We strongly suggest that the contents of this method only be modified using the
        Visual Designer to avoid any incompatible modifications.
        
        Modifying the contents of this method using a code editor will invalidate any support for this file. */
        THIS-OBJECT:ultraButton1 = NEW Infragistics.Win.Misc.UltraButton().
        THIS-OBJECT:ultraComboEditor1 = NEW Infragistics.Win.UltraWinEditors.UltraComboEditor().
        CAST(THIS-OBJECT:ultraComboEditor1, System.ComponentModel.ISupportInitialize):BeginInit().
        THIS-OBJECT:SuspendLayout().
        /*  */
        /* ultraButton1 */
        /*  */
        THIS-OBJECT:ultraButton1:Location = NEW System.Drawing.Point(77, 54).
        THIS-OBJECT:ultraButton1:Name = "ultraButton1":U.
        THIS-OBJECT:ultraButton1:Size = NEW System.Drawing.Size(75, 23).
        THIS-OBJECT:ultraButton1:TabIndex = 0.
        THIS-OBJECT:ultraButton1:Text = "ultraButton1":U.
        THIS-OBJECT:ultraButton1:Click:Subscribe(THIS-OBJECT:ultraButton1_Click).
        /*  */
        /* ultraComboEditor1 */
        /*  */
        THIS-OBJECT:ultraComboEditor1:Location = NEW System.Drawing.Point(55, 107).
        THIS-OBJECT:ultraComboEditor1:Name = "ultraComboEditor1":U.
        THIS-OBJECT:ultraComboEditor1:Size = NEW System.Drawing.Size(144, 21).
        THIS-OBJECT:ultraComboEditor1:TabIndex = 1.
        THIS-OBJECT:ultraComboEditor1:GestureQueryStatus:Subscribe(THIS-OBJECT:ultraComboEditor1_GestureQueryStatus).
        
        /*  */
        /* TestControl */
        /*  */
        THIS-OBJECT:AutoScaleDimensions = NEW System.Drawing.SizeF(Progress.Util.CastUtil:ToSingle(6), Progress.Util.CastUtil:ToSingle(13)).
        THIS-OBJECT:Controls:Add(THIS-OBJECT:ultraComboEditor1).
        THIS-OBJECT:Controls:Add(THIS-OBJECT:ultraButton1).
        THIS-OBJECT:Name = "TestControl":U.
        CAST(THIS-OBJECT:ultraComboEditor1, System.ComponentModel.ISupportInitialize):EndInit().
        THIS-OBJECT:ResumeLayout(FALSE).
        THIS-OBJECT:PerformLayout().
        CATCH e AS Progress.Lang.Error:
            UNDO, THROW e.
        END CATCH.
	END METHOD .

    /**
     * Purpose: 
     * Notes:   
     * @param sender The reference to the object that raised the event
     * @param e The System.EventArgs with the data for this event
     */
	METHOD PRIVATE VOID ultraButton1_Click (INPUT sender AS System.Object, 
                                            INPUT e AS System.EventArgs):
		
		 /* This is supposed to fail, as there's a subscribe, the first parameter is System.Object, but no catch */

	END METHOD .

    /**
     * Purpose: 
     * Notes:   
     * @param sender The reference to the object that raised the event
     * @param e The GestureQueryStatusEventArgs with the data for this event
     */
	METHOD PRIVATE VOID ultraComboEditor1_GestureQueryStatus (sender AS System.Object, 
                                                              e AS Infragistics.Win.Touch.GestureQueryStatusEventArgs):
		
		/* This is not supposed to fail - as there's a subscribe, the first parameter is System.Object
		   and there is a CATCH block */
		
		CATCH err AS Progress.Lang.Error:
        	MESSAGE err:GetMessage(1)
                VIEW-AS ALERT-BOX.	
        END CATCH.

	END METHOD .

    /**
     * Purpose: 
     * Notes:   
     * @param sender The reference to the object that raised the event
     * @param e The System.EventArgs with the data for this event
     */
    METHOD PRIVATE VOID ultraButton2_Click (INPUT sender AS System.Object, 
                                            INPUT e AS System.EventArgs):
        
         /* This is no supposed to fail, as there's NO subscribe! */

    END METHOD .

    /**
     * Purpose: 
     * Notes:   
     * @param sender The reference to the object that raised the event
     * @param e The GestureQueryStatusEventArgs with the data for this event
     */
    @VisualDesigner.
    METHOD PRIVATE VOID ultraComboEditor2_GestureQueryStatus (sender AS System.Object, 
                                                              e AS Infragistics.Win.Touch.GestureQueryStatusEventArgs):
        
        /* This is not supposed to fail - as there's NO subscribe! */
        
        CATCH err AS Progress.Lang.Error:
            MESSAGE err:GetMessage(1)
                VIEW-AS ALERT-BOX.  
        END CATCH.

    END METHOD .


    /**
     * Purpose: Destructor for the TestControl class 
     * Notes:   
     */
    DESTRUCTOR PUBLIC TestControl ():

    END DESTRUCTOR .

END CLASS.
