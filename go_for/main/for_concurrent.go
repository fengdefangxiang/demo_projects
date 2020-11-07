package main

import (
	"context"
	"fmt"
	"golang.org/x/sync/errgroup"
	"time"
)

type Param struct {
	Id int64
}

func main() {
	test3()
}

func test1() {
	// 传递参数的管道
	ch := make(chan *Param)

	// 创建errgroup，便于实现主协程等待子协程的功能
	gCtx, _ := context.WithTimeout(context.Background(), 1*time.Hour)
	g, gCtx := errgroup.WithContext(gCtx)

	// 起子协程往管道里不断传递参数
	g.Go(func() error {
		return send(ch)
	})

	// 在主协程里从管道中取出参数，然后对每个参数起一个子协程去处理
	for param := range ch {
		// 复制param，防止param的值被后续的新值覆盖
		tempParam := param
		fmt.Printf("get param： %+v\n", tempParam)
		g.Go(func() error {
			return dealParam(tempParam)
		})
	}

	// 等待所有子协程处理完毕
	if err := g.Wait(); err != nil {
		fmt.Printf("err: %+v\n", err)
	}

}

func send(ch chan *Param) error {
	for i := 1; ; i++ {
		p := Param{Id: int64(i)}
		ch <- &p
	}
}

func dealParam(param *Param) error {
	fmt.Printf("deal param: %+v\n", param)

	return nil
}

func test2() {
	params := []*Param{}
	for i := 0; i < 100; i++ {
		params = append(params, &Param{Id: int64(i)})
	}

	gCtx, _ := context.WithTimeout(context.Background(), 1*time.Hour)
	g, gCtx := errgroup.WithContext(gCtx)

	for _, param := range params {
		g.Go(func() error {
			return dealParam(param)
		})
	}

	if err := g.Wait(); err != nil {
		fmt.Printf("err: %+v\n", err)
	}
}

func test3() {
	params := []*Param{}
	for i := 0; i < 100; i++ {
		params = append(params, &Param{Id: int64(i)})
	}

	for _, param := range params {
		go dealParam(param)
	}

	time.Sleep(10*time.Second)
}
