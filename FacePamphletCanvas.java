/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.awt.Image.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}
	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		GLabel message = new GLabel(msg);
		double x = getWidth()/2.0 - message.getWidth()*0.75;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		if(getElementAt(oldX, oldY) != null) {
			remove(getElementAt(oldX, oldY));
		}
		oldX = x;
		oldY = y;
		message.setFont(MESSAGE_FONT);
		add(message, x, y);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		ProfileName(profile.getName());
		ProfileImage(profile.getImage());
		ProfileStatus(profile.getStatus());
		FacePamphletFriends(profile.getFriends());
	}
	
	private void ProfileName(String name){
		GLabel Name = new GLabel(name);
		Name.setFont(PROFILE_NAME_FONT);
		Name.setColor(Color.BLUE);
		Name.setFont("Helvetica");
		double x = LEFT_MARGIN;
		nameVal = Name.getHeight();
		double y = TOP_MARGIN + nameVal;
		add(Name, x, y);
	}
	
	private void ProfileImage(GImage image) {
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + nameVal + IMAGE_MARGIN; 
		if(image != null) {
			image.setBounds(x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image);
		}else{
			GRect imageRect = new GRect(x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(imageRect);
			GLabel noImage = new GLabel("No Image");
			noImage.setFont(PROFILE_IMAGE_FONT);
			double labelWidth = x + (IMAGE_WIDTH - noImage.getWidth())/2.0;
			double labelHeight = y + IMAGE_HEIGHT/2.0;
			add(noImage, labelWidth, labelHeight);
		}
	}
	
	private void ProfileStatus(String status) {
		GLabel Status = new GLabel(status);
		Status.setFont(PROFILE_STATUS_FONT);
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + nameVal + IMAGE_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + Status.getHeight();
		if(getElementAt(x, y) != null) {
			remove(getElementAt(x, y));
		}
		add(Status, x, y);
	}

	private void FacePamphletFriends(Iterator<String>friends) {
		GLabel Friends = new GLabel("Friends:");
		Friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		double x = getWidth()/2.0;
		double y = TOP_MARGIN + nameVal;
		add(Friends, x, y);
		Iterator<String> it = friends;
		for(int i = 1; it.hasNext(); i++) {
			GLabel friendName = new GLabel(it.next());
			friendName.setFont(PROFILE_FRIEND_FONT);
			double height = y + Friends.getHeight() * i;
			add(friendName, x, height);
		}
	}

	double nameVal = 0.0;
	double oldX = 0.0;
	double oldY = 0.0;
	
}