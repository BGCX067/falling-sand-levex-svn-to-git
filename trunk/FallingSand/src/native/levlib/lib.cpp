#include <jni.h>
#include <stdio.h>
#include <iostream>
#include "natives.h"

using namespace std;

void fillThat(JNIEnv* env, jstring* s, char* str);

const char* VERSION = "0.3_10";
const char* TITLE = "Falling Sand ";

char* ERASER = "Nothing";
char* SAND = "Sand";
char* WALL = "Wall";
char* WATER = "Water";
char* PLANT = "Plant";
char* FIRE = "Fire";
char* ACID = "Acid";
char* OIL = "Oil";
char* STEAM = "Steam";
char* TORCH = "Torch";
char* WATER_TORCH = "Water Torch";
char* WATER_TAP_OPENED = "Water tap";
char* WATER_TAP_CLOSED = "Water tap";
char* SALT = "Salt";
char* SALT_WATER = "Salt water";
char* FILTER = "Filter";
char* OIL_TORCH = "Oil Torch";
char* SAND_TORCH = "Sand Torch";
char* SMOKE = "Smoke";
char* TNT = "TNT";
char* IRON = "Iron";
char* CIGARETTE = "Cigarette";
char* ACID_FILTER = "Acid Filter";
char* ANTI_ACID = "Lye";
char* SALT_FILTER = "Salt Filter";
char* MAGMA = "Magma";

/* @return nothing */
JNIEXPORT void JNICALL Java_org_levex_games_fallingsand_Start_initNatives
  (JNIEnv * env, jclass c, jint lang)
{
          if(lang == 1) {
                  cout << "Natives have been initialized." << endl;
          } else {
                 ERASER = "Semmi";
                 SAND = "Homok";
                 WALL = "Fal";
                 WATER = "Víz";
                 PLANT = "Növény";
                 FIRE = "Tûz";
                 ACID = "Sav";
                 OIL = "Olaj";
                 STEAM = "Gõz";
                 TORCH = "Tûzcsináló";
                 WATER_TORCH = "Forrás";
                 WATER_TAP_OPENED = "Vízcsap";
                 WATER_TAP_CLOSED = "Vízcsap";
                 SALT = "Só";
                 FILTER = "Szûrõ";
                 OIL_TORCH = "Olaj generáló";
                 SAND_TORCH = "Homok vár";
                 SMOKE = "Füst";
                 TNT = "TNT";
                 IRON = "Vas";
                 CIGARETTE = "Cigaretta";
                 ACID_FILTER = "Sav Szürõ";
                 ANTI_ACID = "Lúg";
                 SALT_FILTER = "Só Szûrõ";
                 cout << "Nativok betoltve" << endl;
          }
}


/* @return the VERSION of the Falling Sand */
JNIEXPORT jstring JNICALL Java_org_levex_games_fallingsand_Start_getVersion
  (JNIEnv * env, jclass c)
{
          jstring result = env->NewStringUTF(VERSION);
          return result;
}

/* @return the WINDOWS_TITLE of the Falling Sand */
JNIEXPORT jstring JNICALL Java_org_levex_games_fallingsand_Start_getWindowName
  (JNIEnv * env, jclass c)
{
          jstring result = env->NewStringUTF(TITLE);
          return result;
}

JNIEXPORT jstring JNICALL Java_org_levex_games_fallingsand_Start_getNameOfSand
  (JNIEnv *env, jclass jc, jint sand)
{
          jstring result;
          switch(sand)
          {
                      case 0:
                           fillThat(env, &result, ERASER);
                           return result;
                      case 1:
                           fillThat(env, &result, SAND);
                           return result;
                      case 2:
                           fillThat(env, &result, WALL);
                           return result;
                      case 3:
                           fillThat(env, &result, WATER);
                           return result;
                      case 4:
                           fillThat(env, &result, PLANT);
                           return result;
                      case 5:
                           fillThat(env, &result, FIRE);
                           return result;
                      case 6:
                           fillThat(env, &result, ACID);
                           return result;
                      case 7:
                           fillThat(env, &result, OIL);
                           return result;
                      case 8:
                           fillThat(env, &result, STEAM);
                           return result;
                      case 9:
                           fillThat(env, &result, TORCH);
                           return result;
                      case 10:
                           fillThat(env, &result, WATER_TORCH);
                           return result;
                      case 11:
                           fillThat(env, &result, WATER_TAP_CLOSED);
                           return result;
                      case 12:
                           fillThat(env, &result, WATER_TAP_OPENED);
                           return result;
                      case 13:
                           fillThat(env, &result, SALT);
                           return result;
                      case 14:
                           fillThat(env, &result, SALT_WATER);
                           return result;
                      case 15:
                           fillThat(env, &result, FILTER);
                           return result;
                      case 16:
                           fillThat(env, &result, OIL_TORCH);
                           return result;
                      case 17:
                           fillThat(env, &result, SAND_TORCH);
                           return result;
                      case 18:
                           fillThat(env, &result, SMOKE);
                           return result;
                      case 19:
                           fillThat(env, &result, TNT);
                           return result;
                      case 20:
                           fillThat(env, &result, IRON);
                           return result;
                      case 21:
                           fillThat(env, &result, CIGARETTE);
                           return result;
                      case 22:
                           fillThat(env, &result, ACID_FILTER);
                           return result;
                      case 23:
                           fillThat(env, &result, ANTI_ACID);
                           return result;
                      case 24:
                           fillThat(env, &result, SALT_FILTER);
                           return result;
                      case 25:
                           fillThat(env, &result, MAGMA);
                           return result;
          }
                               
}


void fillThat(JNIEnv* env, jstring* s, char* str) {
     *s = env->NewStringUTF(str);
}
