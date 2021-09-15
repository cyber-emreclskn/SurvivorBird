package com.emrecaliskan.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bee1;
	Texture bee2;
	Texture bee3;
	float birdx = 0;
	float birdy = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.4f;
	float enemyVelocity = 5;

	Circle birdCirle;
	ShapeRenderer shapeRenderer;
	Random random;

	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOffSet = new float[numberOfEnemies];
	float [] enemyOffSet2 = new float[numberOfEnemies];
	float [] enemyOffSet3 = new float[numberOfEnemies];

	Circle[] enemyCirles;
	Circle[] enemyCirles2;
	Circle[] enemyCirles3;

	float distance = 0;

	int score = 0;
	int scoredEnemy = 0;

	BitmapFont font;
	BitmapFont font2;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		bee1 = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();

		birdx = Gdx.graphics.getWidth()/2 - Gdx.graphics.getHeight()/2;
		birdy = Gdx.graphics.getHeight()/3;

		shapeRenderer = new ShapeRenderer();

		birdCirle = new Circle();
		enemyCirles = new Circle[numberOfEnemies];
		enemyCirles2 = new Circle[numberOfEnemies];
		enemyCirles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(7);

		for (int i = 0; i < numberOfEnemies; i++){

			enemyOffSet[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
			enemyOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
			enemyOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

			enemyCirles[i] = new Circle();
			enemyCirles2[i] = new Circle();
			enemyCirles3[i] = new Circle();

			enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2 + i*distance;
		}
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState == 1){

			if(enemyX[scoredEnemy] < Gdx.graphics.getWidth()/2 - Gdx.graphics.getHeight()/2){
				score++;
				if (scoredEnemy < numberOfEnemies - 1){
					scoredEnemy++;
				}else{
					scoredEnemy = 0;
				}
			}


			if (Gdx.input.justTouched()){
				velocity = -10;
			}

			for (int i = 0; i < numberOfEnemies; i++){

				if (enemyX[i] < Gdx.graphics.getWidth()/15){
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 250);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 250);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 350);

				}else{
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				batch.draw(bee1,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(bee2,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(bee3,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);


				enemyCirles[i] = new Circle(enemyX[i] + Gdx.graphics.getHeight()/30 ,  + Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCirles2[i] = new Circle(enemyX[i] + Gdx.graphics.getHeight()/30 ,  + Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCirles3[i] = new Circle(enemyX[i] + Gdx.graphics.getHeight()/30 ,  + Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

			}


			//Gravity
			if (birdy > 0){
				velocity += gravity;
				birdy -= velocity;
			}else{
				gameState = 2;
			}

		}else if(gameState == 0){
			if (Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if(gameState == 2){

			font2.draw(batch,"Game Over! Tap to Play Again!!",100,Gdx.graphics.getHeight()/2);

			if (Gdx.input.justTouched()){
				gameState = 1;
				birdy = Gdx.graphics.getHeight()/3;

				for (int i = 0; i < numberOfEnemies; i++){

					enemyOffSet[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
					enemyOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
					enemyOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

					enemyCirles[i] = new Circle();
					enemyCirles2[i] = new Circle();
					enemyCirles3[i] = new Circle();

					enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2 + i*distance;
				}

				velocity = 0;
				scoredEnemy = 0;
				score = 0;

			}
		}


		batch.draw(bird,birdx,birdy,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		birdCirle.set(birdx + Gdx.graphics.getWidth()/30,birdy + Gdx.graphics.getWidth()/20,Gdx.graphics.getWidth()/30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCirle.x,birdCirle.y,birdCirle.radius);


		for (int i = 0; i < numberOfEnemies; i++){
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getHeight()/30 ,  + Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getHeight()/30 ,  + Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getHeight()/30 ,  + Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

			if (Intersector.overlaps(birdCirle,enemyCirles[i]) || Intersector.overlaps(birdCirle,enemyCirles2[i]) || Intersector.overlaps(birdCirle,enemyCirles3[i])){
				gameState = 2;
			}

		}
		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
