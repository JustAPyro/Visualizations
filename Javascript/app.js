class Maze {

    /* Primary constructor */
    constructor(canvas) {

        // Save the canvas we're working with
        this.canvas = canvas;

        // create an 2-D array of horizontal walls
        this.horizontalWalls = new Array(6);
        for (let i = 0; i < 6; i++) {
            this.horizontalWalls[i] = new Array(6);
            for (let j = 0; j < 6; j++) {
                this.horizontalWalls[i][j] = 1;
            }
        }

        // create a 2-D array of vertical walls
        this.verticalWalls = new Array(6);
        for (let i = 0; i < 6; i++) {
            this.verticalWalls[i] = new Array(6);
            for (let j = 0; j < 6; j++) {
                this.verticalWalls[i][j] = 0;
            }
        }


    }

    /* Draws the current maze state */
    draw() {
        const ctx = this.canvas.getContext('2d')


        // set line stroke and line width
        ctx.lineWidth = 1;

        const size = 50;
        const color = {
            0 : 'red',
            1 : 'blue'
        };

        for (let r = 0; r < 5; r++) {
            for (let c = 0; c < 5; c++) {

                ctx.strokeStyle = color[this.horizontalWalls[r][c]];

                // draw horizontal lines
                ctx.beginPath();
                ctx.moveTo(c*size, size*r+1);
                ctx.lineTo((c+1)*size, size*r+1);
                ctx.stroke();

                ctx.strokeStyle = color[this.verticalWalls[r][c]];

                // draw vertical lines
                ctx.beginPath();
                ctx.moveTo(r*size+1, c*size);
                ctx.lineTo(r*size+1, (c+1)*size);
                ctx.stroke();

            }

            ctx.strokeStyle = color[this.horizontalWalls[r][5]];

            // Draw final (outer) horizontal lines
            ctx.beginPath();
            ctx.moveTo(r*size, 5*size+1);
            ctx.lineTo((r+1)*size, 5*size+1);
            ctx.stroke();

            ctx.strokeStyle = color[this.verticalWalls[r][5]];

            // Draw final (outer) vertical lines
            ctx.beginPath();
            ctx.moveTo(5*size+1, r*size);
            ctx.lineTo(5*size+1, (r+1)*size);
            ctx.stroke();


        }

    }

}



const canvas = document.querySelector('#canvas');
let maze = new Maze(canvas);
maze.draw();