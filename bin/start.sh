#!/usr/bin/env bash

# Implement this function to specify the platform your service is built on.
select_platform () {
  local func_result="openjdk:8"
  echo "$func_result"
}


# Implement this function so that when executed it would build and start your service.
run_service() {
    # The commands to build and start the service go here.
	#docker build -t egym-exercise .
	func_result="$(select_platform)"
	echo $func_result
	docker build -t egym-exercise --build-arg base_image=$func_result .
	docker run -p 8080:8080 egym-exercise
}
run_service



