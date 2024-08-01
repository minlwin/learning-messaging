import { Injectable, signal } from "@angular/core"

@Injectable({providedIn: 'root'})
export class ProgressListener {

  progress = signal<Progress | undefined>(undefined)

  connect(historyId:string) {

    const ws = new WebSocket(`ws://localhost:8080/esc/progress/${historyId}`)

    ws.onclose = () => this.progress.set(undefined)

    ws.onmessage = (message) => {
      console.log(message.data)
      this.progress.set(message.data)
    }

  }

}

export interface Progress {
  historyId:string
  state:string
  done?:number
  total?:number
  percent?:number
  message?:string
}
