#include <map>
#include "Scene.hpp"
#include <iostream>
#include <GLFW/glfw3.h>
Scene::~Scene(){
    for (int i=0;i<sceneObjects.size();i++)
    {
        delete sceneObjects[i];
    }
    sceneObjects.clear();
}


void Scene::render(Camera* camera){
    //Ensure we render the objects from furthest to closest to enable correct blending
    //Sort based on distance
    std::map<float, Object*> sorted;
    for (unsigned int i = 0; i < sceneObjects.size(); i++)
    {   
        Object* obj = sceneObjects[i];
        glm::vec3 vec = obj->getPosition();
        float distance = glm::length(camera->getPosition() - obj->getPosition());
        sorted[distance] = sceneObjects[i];
    }
  
    //Render on decending order
    for(std::map<float,Object*>::reverse_iterator it = sorted.rbegin(); it != sorted.rend(); ++it) 
    {
        it->second->render(camera);			

    }  
 
}

void Scene::addObject(Object *object){
    sceneObjects.push_back(object);
    
}


