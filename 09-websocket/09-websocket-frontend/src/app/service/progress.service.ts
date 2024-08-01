import { Injectable, signal } from "@angular/core";

@Injectable({providedIn: 'root'})
export class ProgressService {

  message = signal<string[]>([])
  connected = signal<boolean>(false)

  listen(historyId:string) {

    const ws = new WebSocket(`ws://localhost:8080/ws/${historyId}`)

    ws.onopen = () => {
      this.connected.set(true)
    }

    ws.onmessage = (event) => {
      this.message.set([event.data, ...this.message()])
    }

    ws.onclose = () => {
      this.message.set([])
      this.connected.set(false)
    }
  }
}
