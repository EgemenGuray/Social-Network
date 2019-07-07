/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		/*
		 * North Buttons
		 */
		add(new JLabel("Name "), NORTH); 
		ProfileName = new JTextField(TEXT_FIELD_SIZE);
		add(ProfileName, NORTH);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		/*
		 * West Buttons
		 */
		StatusUpdate = new JTextField(TEXT_FIELD_SIZE);
		add(StatusUpdate, WEST);
		add(new JButton("Change Status"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST); 
		ProfilePicture = new JTextField(TEXT_FIELD_SIZE);
		add(ProfilePicture, WEST);
		add(new JButton("Change Picture"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		FacePamphletFriend = new JTextField(TEXT_FIELD_SIZE);
		add(FacePamphletFriend, WEST);
		add(new JButton("Add Friend"), WEST);
		/*
		 * Action listeners
		 */
		addActionListeners();
		StatusUpdate.addActionListener(this);
		ProfilePicture.addActionListener(this);
		FacePamphletFriend.addActionListener(this);
		add(Canvas);
    }
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
	
    public void actionPerformed(ActionEvent e) {
    	String enteredName = ProfileName.getText();
    	/*
    	 * Waits for the button pressed and checks for the name string
    	 */
    	if((e.getActionCommand().equals("Add")) && (!ProfileName.getText().equals(""))){
    		/*
    		 * If name deosn't exist adds else gets the existed profile
    		 */
    		if(ProfileInfo.containsProfile(enteredName) == false){
    			FacePamphletProfile profile = new FacePamphletProfile(enteredName);
    			ProfileInfo.addProfile(profile);
    			Canvas.displayProfile(profile);
    			Canvas.showMessage("New profile created");
    			Profile = profile;
    		}else{
    			FacePamphletProfile profile = ProfileInfo.getProfile(enteredName);
    			Canvas.displayProfile(profile);
    			Canvas.showMessage("A profile with name " + enteredName + " already exists.");
    			Profile = profile;
    		}
    		/*
    		 * 	Waits for the delete button if there is name deletes if it does'nt exist says does'nt exist
    		 */
    	}else if((e.getActionCommand().equals("Delete")) && (!ProfileName.getText().equals(""))){
    		Canvas.removeAll();
    		Profile = null;
    		if(ProfileInfo.containsProfile(enteredName) == true){
    			ProfileInfo.deleteProfile(enteredName);
    			Canvas.showMessage("Profile of " + enteredName + " deleted");
    		}else{
    			Canvas.showMessage("A profile with name " + enteredName + " does'nt exist.");
    		}
    		/*
    		 * Waits for the look up button and if there is name shows it if profoli doesn't exist says doesn't exist
    		 */
    	}else if((e.getActionCommand().equals("Lookup")) && (!ProfileName.getText().equals(""))){
    		Canvas.removeAll();
    		if(ProfileInfo.containsProfile(enteredName) == true){
    			FacePamphletProfile profile = ProfileInfo.getProfile(enteredName);
    			Canvas.displayProfile(profile);
    			Canvas.showMessage("Displaying " + enteredName);
    			Profile = profile;
    		}else{
    			Canvas.showMessage("A profile with name " + enteredName + " does not exist.");
    		}
    		/*
    		 * Waits for the change status button changes status if there is no profile displaying shows message
    		 */
    	}else if((e.getActionCommand().equals("Change Status")) || ((e.getSource() == StatusUpdate && !StatusUpdate.getText().equals("")))){
    		String statusMessage = StatusUpdate.getText();
    		if(Profile != null){
    			FacePamphletProfile profile = ProfileInfo.getProfile(Profile.getName());
    			profile.setStatus(profile.getName() + " is " + statusMessage);
    			Canvas.displayProfile(profile);
    			Canvas.showMessage("Status updated to " + statusMessage);
    		}else{
    			Canvas.showMessage("Please select a profile to change status");
    		}
    		/*
    		 * Waits for the change picture gets the picture changes 
    		 */
    	}else if((e.getActionCommand().equals("Change Picture")) || (e.getSource() == ProfilePicture && !ProfilePicture.getText().equals(""))){
    		String filename = ProfilePicture.getText();
    		if(Profile != null){
    			FacePamphletProfile profile = ProfileInfo.getProfile(Profile.getName());
    			GImage image = null;
    			try{
    				image = new GImage(filename);
    				profile.setImage(image);
    			}catch (ErrorException ex){
    				image = null;
    			}
    			Canvas.displayProfile(profile);
    			if(image == null){
    				Canvas.showMessage("Unable to open image file: " + filename);
    			}else{
    				Canvas.showMessage("Picture updated");
    			}
    		}else{
    			println("Please select a profile to change picture");
    		}
    		/*
    		 * Waits for the add friend button checks for who is the person you are trying to add as a friend
    		 */
    	}else if((e.getActionCommand().equals("Add Friend")) || (e.getSource() == FacePamphletFriend && !FacePamphletFriend.getText().equals(""))){
    		String friendName = FacePamphletFriend.getText();
    		if(Profile != null){
    			FacePamphletProfile profile = ProfileInfo.getProfile(Profile.getName());
    			if(profile.getName().equals(friendName)){
    				Canvas.showMessage("You cannot friend yourself");
    			}else if(ProfileInfo.containsProfile(friendName)){
    				FacePamphletProfile friendProfile = ProfileInfo.getProfile(friendName);
    				if(profile.addFriend(friendName) == true){
    					profile.addFriend(friendName);
    					friendProfile.addFriend(enteredName);
    					Canvas.displayProfile(profile);
    					Canvas.showMessage(friendName + " added as a friend.");
    				}else{
    					Canvas.showMessage(profile.getName() + " already has " + friendName + " as a friend.");
    				}
    			}else{
    				Canvas.showMessage(friendName + " does not exist.");
    			}
    		}else{
    			Canvas.showMessage("Please select a profile to add friend");
    		}	
    	}		
    }
    private JTextField ProfileName;
    private JTextField ProfilePicture;
	private JTextField StatusUpdate;
	private JTextField FacePamphletFriend;
	private FacePamphletDatabase ProfileInfo = new FacePamphletDatabase();
	private FacePamphletCanvas Canvas = new FacePamphletCanvas();
	private FacePamphletProfile Profile = null;
}