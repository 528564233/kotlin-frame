package com.base.app.ui.dialog


interface IDialogInterface {

    interface NoticeSuccess {
        fun dialogSuccess(
            string: String, type: Int
        )

        fun dialogCancel(
        )
    }

    interface NoticeSuccessData {
        fun dialogSuccess(
            data: Any
        )

        fun dialogCancel(
        )
    }
    interface CompressSuccess {
        fun dialogSuccess(
            data: Any
        )
        fun hideLoading(
        )
        fun showLoading(
        )

    }

    companion object {
        const val CAMERA = 1002//相机
        const val ALBUM = 1003//

    }
}