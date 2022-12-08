

import com.base.app.base.BaseModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RxTransformerUtils {
    /**
     * 无参数
     *
     * @param <T> 泛型
     * @return 返回Observable
    </T> */
    companion object{
        fun <T> switchSchedulers(baseModel: BaseModel): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream: Observable<T> ->
                upstream
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .doOnSubscribe { disposable: Disposable? ->
                        baseModel.mDisposable.add(
                            disposable!!
                        )
                    }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}