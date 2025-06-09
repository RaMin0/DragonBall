default: gui

compile:
	@mkdir -p bin
	@javac -g -sourcepath src -d bin $(shell find src -name '*.java')
	@cp -f *.csv bin/ 2>/dev/null || true
	@cp -R res sfx gfx bin/ 2>/dev/null || true

jar: compile
	@jar cfm DragonBall.jar MANIFEST.MF -C bin .

gui: compile
	@java -cp bin dragonball.controller.DragonBallGUI

console: compile
	@java -cp bin dragonball.controller.DragonBallConsole

clean:
	@rm -rf bin DragonBall.jar

.PHONY: default compile jar gui console clean
