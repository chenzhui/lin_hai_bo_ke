import {ImageManager} from "@/JS/ImageManager";
export default class Lazy {
    constructor() {
        this.managerQueue = []
        this.initIntersectionObserver()
    }
    add(el,binding) {
        const src = binding.value
        const imageManager = new ImageManager({el, src})
        this.managerQueue.push(imageManager)
        this.observer.observe(el)
    }
    initIntersectionObserver() {
        this.observer = new IntersectionObserver((entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    const manager = this.managerQueue.find((manager)=>{return manager.el === entry.target})
                    if (manager){
                        if (manager.state === 1) {this.removeManager(manager);return}
                        manager.renderSrc(manager.src)
                    }
                }
            })
        })
    }
    removeManager(manager) {
        const index = this.managerQueue.indexOf(manager)
        if (index > -1) {this.managerQueue.splice(index, 1)}
        if (this.observer) {this.observer.unobserve(manager.el)}
    }
}

