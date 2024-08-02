import { Injectable, signal } from "@angular/core"
import { environment } from "../../../environments/environment"

@Injectable({providedIn: 'root'})
export class ProgressListener {

  progress = signal<Progress | undefined>(undefined)
  connected = signal<boolean>(false)

  connect(historyId:string) {

    const ws = new WebSocket(`${environment.wsUrl}/progress/${historyId}`)

    ws.onopen = () => this.connected.set(true)
    ws.onclose = () => this.connected.set(false)
    ws.onerror = () => this.connected.set(false)

    ws.onmessage = (message) => {
      this.progress.set(JSON.parse(message.data))
    }
  }
}

export interface Progress {
  historyId:string
  state:string
  done?:number
  total?:number
  percent?:string
  message?:string
}
