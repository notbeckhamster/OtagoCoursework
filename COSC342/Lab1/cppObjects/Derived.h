#pragma once

#include "Object.h"

class Derived : public Object {

public:

	Derived();
	Derived(const Derived& d);
	~Derived();

	Derived& operator=(const Derived& d);

	void whatAmI() const;

private:

};