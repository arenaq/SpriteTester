package org.arenaq.spritetester;

import android.content.Context;
import android.opengl.GLSurfaceView; 

public class SFGameView extends GLSurfaceView {
	private PrinceRenderer renderer;
	
	public SFGameView(Context context) {
		super(context);		
		renderer = new PrinceRenderer();
		this.setRenderer(renderer);		
	} 
} 
