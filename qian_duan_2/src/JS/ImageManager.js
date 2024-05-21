
export class ImageManager {
    constructor(options) {
        this.el = options.el
        this.src = options.src
        this.state =0
    }
    renderSrc(src) {
        if(this.state>0){return}
        this.el.setAttribute('src', src)
        this.state = 1
    }
}
