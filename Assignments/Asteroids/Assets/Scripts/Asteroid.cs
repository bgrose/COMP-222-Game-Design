using System.Collections;
using System.Collections.Generic;
using UnityEngine;

//* @class: Asteroids
//* @brief This class is the creater for asteroids
//* @author: Bradley Grose
//* @date: 17/03/2022

public class Asteroid: MonoBehaviour {

  //Collection of Asteroids images
  public Sprite[] asteroidSprites;

  //Sprite Controls
  private SpriteRenderer spriteRenderer;
  private Rigidbody2D rb;

  //Size Holder
  public float size = 1.0f;

  //Size Constant
  public readonly float MINSIZE = 70f;
  public readonly float MAXSIZE = 150f;
  public readonly float MAXSPLIT = 90f;

  //* @summary: Initialize the asteroid
  //* @param: none
  //* @return: none
  private void Awake() {
    spriteRenderer = GetComponent < SpriteRenderer > ();
    rb = GetComponent < Rigidbody2D > ();
  }

  //* @summary: Initializes the asteroid of random size and shape
  //* @param: none
  //* @return: none
  private void Start() {
    Awake();

    //Was coded to have multiple sprites but was not used
    spriteRenderer.sprite = asteroidSprites[Random.Range(0, asteroidSprites.Length)];
    //Add a random force to the asteroid
    GetComponent < Rigidbody2D > ().AddForce(new Vector2(Random.Range(400.0f, 600.0f), Random.Range(400.0f, 600.0f)));

    //Checks speed and improves speed if needed
    if (GetComponent < Rigidbody2D > ().velocity.magnitude < 500.0f) {
      GetComponent < Rigidbody2D > ().AddForce(new Vector2(Random.Range(500.0f, 700.0f), Random.Range(500.0f, 700.0f)));
    }
    transform.eulerAngles = new Vector3(0, 0, Random.Range(0.0f, 360.0f));
    transform.localScale = Vector3.one * size;

    rb.mass = size;
  }

  //* @summary: Set Angle for Asteroid
  //* @param: direction: the angle of the asteroid
  //* @return: none
  public void setAngle(Vector2 direction) {
    //Make speed random
    float speed = Random.Range(400.0f, 600.0f);
    rb.AddForce(direction * speed);
  }

  //* @summary: Splits the asteroid into two smaller asteroids
  //* @param collision: the collision that occurs
  //* @return: none
  private void OnCollisionEnter2D(Collision2D collision) {
    if (collision.gameObject.tag == "Bullet") {
      //Adds to Score
      FindObjectOfType < GameRunner > ().setScore();
      if (size > MAXSPLIT) {
        //Break Astroid into two in different directions and speeds
        for (int i = 0; i < 2; i++) {
          FindObjectOfType < SpawnElements > ().Spawn(transform.position, transform.eulerAngles.z + Random.Range(0.0f, 270.0f), size * .5f);
        }
        Destroy(gameObject);
      } else {
        Destroy(gameObject);
      }
    }
  }
}