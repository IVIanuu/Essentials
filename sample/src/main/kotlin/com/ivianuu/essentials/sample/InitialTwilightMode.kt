package com.ivianuu.essentials.sample

import com.ivianuu.essentials.store.*
import com.ivianuu.essentials.twilight.data.*
import com.ivianuu.injekt.*

@Provide val initialTwilightPrefs: @Initial TwilightPrefs
  get() = TwilightPrefs(TwilightMode.DARK, true)
