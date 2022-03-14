function main() {
    const canvas = document.querySelector('#canvas');

    if (!canvas.getContext) {
        return;
    }

    let maze = new Maze(canvas, 6, 6);
    maze.draw();

}

class Maze {

    constructor(canvas, width, height) {

        // Save the canvas as well
        this.canvas = canvas;

        // Get the context to draw with
        this.ctx = canvas.getContext('2d');

        // save the width and height
        this.width = width; this.height = height;

    }

    draw() {

        this.line([0, 0], [0, this.canvas.height], 'black', 1);
        this.line([0, this.canvas.height], [this.canvas.width, this.canvas.height], 'black', 1);
        this.line([this.canvas.width, this.canvas.height], [this.canvas.width, 0], 'black', 1);
        this.line([this.canvas.width, 0], [0, 0], 'black', 1);
    }

    line(begin, end, stroke = 'black', width = 1) {
        if (stroke) {
            this.ctx.strokeStyle = stroke;
        }

        if (width) {
            this.ctx.lineWidth = width;
        }

        this.ctx.beginPath();
        this.ctx.moveTo(...begin);
        this.ctx.lineTo(...end);
        this.ctx.stroke();
    }


}

main();