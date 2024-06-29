#version 330 core

in vec2 UV;

out vec3 color;

uniform sampler2D renderedTexture;
uniform float time;
const vec2 targetSize = vec2(1024, 768);
const float relativeOffsetX = 1.0/targetSize.x;
const float relativeOffsetY = 1.0/targetSize.y;
//sobel 
vec4 sobelFilter(vec2 UV)
{
    vec4 top = texture(renderedTexture, vec2(UV.x, UV.y + relativeOffsetY));
    vec4 bottom = texture(renderedTexture, vec2(UV.x, UV.y - relativeOffsetY));
    vec4 left = texture(renderedTexture, vec2(UV.x - relativeOffsetY, UV.y));
    vec4 right = texture(renderedTexture, vec2(UV.x + relativeOffsetY, UV.y));

    vec4 bottomRight = texture(renderedTexture, vec2(UV.x + relativeOffsetX, UV.y + relativeOffsetY));
    vec4 bottomLeft = texture(renderedTexture, vec2(UV.x - relativeOffsetX, UV.y + relativeOffsetY));
    vec4 topLeft = texture(renderedTexture, vec2(UV.x - relativeOffsetX, UV.y - relativeOffsetY));
    vec4 topRight = texture(renderedTexture, vec2(UV.x + relativeOffsetX, UV.y - relativeOffsetY));   

    vec4 sx = -topLeft - 2*left - bottomLeft + topRight + 2*right + bottomRight;
    vec4 sy = -topLeft - 2*top - topRight + bottomLeft + 2*bottom + bottomRight;

    vec4 res = sqrt(sx*sx + sy*sy);
    return res;
}
    
vec4 boxBlurFilter(vec2 UV)
{
    vec4 sum = vec4(0.0);
    for (int x = -4; x <= 4; x++){
        for (int y = -4; y <= 4; y++){
            sum += texture(renderedTexture, vec2(UV.x + x*relativeOffsetX, UV.y + y*relativeOffsetY))/81.0;
        }
    }
    return sum;

}

void main(){
	//color = texture( renderedTexture, UV + 0.02*vec2( sin(time+1024.0*UV.x),cos(time+768.0*UV.y)) ).xyz;
   // color = sobelFilter(UV + 0.005*vec2( sin(time+1024.0*UV.x),cos(time+768.0*UV.y))).xyz;
   color = sobelFilter(UV).xyz;
}

